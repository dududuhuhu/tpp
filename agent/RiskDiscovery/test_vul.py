# vuln_server.py
from flask import Flask, request
import pymysql

app = Flask(__name__)

# 连接数据库
def get_db_connection():
    return pymysql.connect(
        host="127.0.0.1",
        user="root",
        password="123456xsy",
        database="threat_perception",
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )

    # db_config = {
    #     'host': 'localhost',
    #     'port': 3306,
    #     'user': 'root',
    #     'password': '123456xsy',
    #     'database': 'threat_perception'
    # }

@app.route("/", methods=["GET"])
def vulnerable_endpoint():
    user_id = request.args.get("id", "")
    try:
        connection = get_db_connection()
        with connection.cursor() as cursor:
            # ⚠️ 存在注入漏洞：字符串拼接方式执行 SQL
            sql = f"SELECT * FROM users WHERE id = '{user_id}'"
            print("执行SQL:", sql)
            cursor.execute(sql)
            result = cursor.fetchone()

            if result and result.get("username") == "admin":
                return "Welcome admin"
            else:
                return "User not found"

    except Exception as e:
        return f"Error: {str(e)}"

    finally:
        connection.close()

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=8080)
