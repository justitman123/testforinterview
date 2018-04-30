<img src="https://travis-ci.org/justitman123/testforinterview.svg?branch=master" alt="Build Status" />
<br>
Приложения создавалось с использованием: Java 8, PostgreSQL и Maven.
<br>
В файле src/main/resources/application.yml указаны 
<br>
<ol>
<li>port: 8082</li>
<li>url: jdbc:postgresql://localhost:5432/postgres</li>
<li>username: postgres</li>
<li>password: root</li>
</ol>
которые указывают порт на котором запускается приложение, а также коннекшн к базе данных. Для запуска приложения необходимо заменить настройки на актуальные для Вашей БД. Также может потребоваться поменять порт 8082 на тот который Вам доступен, либо отключить приложение которое его использует.
<br>
Так же для инициализации скриптов разворота базы данных (если необходимо) раскомментировать 
<br>
schema: classpath:/db/schema.sql
<br>
data:  classpath:/db/populatedb.sql.
<br>
После разворота будет создана таблица contacts с данными.
<br><br>
Для пуска приложения необходимо собрать проект и запустить с помощью: java -jar contact_loader-1.0-SNAPSHOT.jar
