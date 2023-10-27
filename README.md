# sweaterProject

загрузить этот проект в идею с использованием "Get Project from VCS"\
Так как я что-то напортачил с сервисом авторизации, логином служит буква "u", а пароль генерируется в логах спринга:
>2023-10-27T14:46:56.742+03:00  WARN 3492 --- [  restartedMain] .s.s.UserDetailsServiceAutoConfiguration :\ 
>Using generated security password: 68c6c96e-a7da-4a65-a73c-6370efa360bc\
>This generated password is for development use only. Your security configuration must be updated before running your application in production.

На локальной машине в исполняемой среде psql запустить команду \i "path_to_project\\onCreate_script.sql"\
В конфигурационном файле application.properties поменять имя пользователя и пароль для входа в БД на свои.
