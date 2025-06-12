import subprocess

def list_windows_users():
    result = subprocess.run(["net", "user"], capture_output=True, text=True, shell=True)
    output = result.stdout
    users = []

    capture = False
    for line in output.splitlines():
        if "----" in line:
            capture = True
            continue
        if capture:
            if line.strip() == "":
                break
            users.extend(line.strip().split())

    return users[:-1]

print("系统所有用户：", list_windows_users())
