# coding=utf-8
import wmi

# 初始化
# pythoncom.CoInitialize()
c=wmi.WMI()
account_list=[]
if __name__ == '__main__':
    for user in c.Win32_UserAccount():
        user_dict={
            "name":user.Name,
            "full_name":user.FullName,
            "sid":user.SID,
            "sid_type":user.SIDType,
            "status":user.Status,
            "disabled":user.Disabled,
            "locked_out":user.Lockout,
            "password_changeable":user.PasswordChangeable,
            "password_expires":user.PasswordExpires,
            "password_required":user.PasswordRequired,
        }
        account_list.append(user_dict)
    # 去初始化
    # pythoncom.CoInitialize()




