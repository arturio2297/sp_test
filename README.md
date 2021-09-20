
# Краткое описание #
---
Данное приложение представляет собой простой пример реализации MVC приложения с использование spring security.
В приложении реализованы следующие возможности:
1. Система регистрации и авторизации.
    > Также реализована отправка письма для подтверждения регистрации.
2. Система ролей и прав.
    > Всего реализовано три роли. Каждая из ролей обладает своим набором прав.
3. Простой интерфейс для тестирования функциональности приложения.
    > Видимость некоторых элементов интерфейса зависит от роли и факта авторизации.
---
# Регистрация новых пользователей #
---
Форма для регистрации представлена ниже
---
![форма для регистрации](https://i.ibb.co/yXFHhPW/register.png "registration form")
---       
Для регистрации необходимо чтобы пароль и адрес электронной почты прошли проверку на валидность.
Проверка на валидность пароля стандартная (не менее 8 символов, минимум 1 символ как верхнего, так и нижнего регистров,
один специальный символ и одна цифра). Регулярное выражение для проверки валидноси пароля представлено ниже.
---
<"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$">
---
Проверка на валидность адреса электронной почти реализована с помощью метода "validate()" класса "InternetAddress"

# Подтверждение регистрации #

После заполнения формы и нажатия на кнопку (в случае если введенные пароль и почта валидны) на указанную при регистрации почту будет отправлено сообщение, содержащее html шаблон с ссылкой для подтверждения регистрации. Также в базу данных вносятся: новый пользователь и новый токен (с кодом для подтверждения регистрации).
---
![database схема](https://i.ibb.co/bmkgX08/bd-relationsheps.png  "relationshep between tables")
---
Аккаунт пользователя будет заблокирован до тех пор пока он не подвтердит регистрацию (ссылка валидна 15 минут).
---
![подтверждение регистрации](https://i.ibb.co/wRKNxX1/email.png  "confirmed registration")
---
Если пользователь подтверждает регистрацию, то его аккаунт будет разблокирован и он сможет войти в приложение.

##Возможности пользователей##
В зависимости от токо авторизован ли пользователь и какова его роль изменяются и выводимые элементы "шапки".

В случае неавторизованного пользователя видны следующие элементы.
---
![элементы ui неавторизованного пользователя](https://i.ibb.co/br5G7n7/no-authorized.png "noautorized user visible elements of ui")
---
Для авторизованного пользователя с ролью ("VIEWER" или "WRITER")
---
![элементы ui авторизованного пользователя](https://i.ibb.co/VMXgNT6/authorized.png "autorized user visible elements of ui")
---
И лишь для пользователя с ролью "ADMIN" есть доступ к страничке "adminpanel". На которой выводится основаная информация о пользователях.
---
![admin panel](https://i.ibb.co/vqkNgYk/adminpanel.png)


