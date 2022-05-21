Filmorate project.
![ER diagram](https://github.com/MadeOfSt0ne/java-filmorate/blob/add-friends-likes/filmorateERdiagram.png)
1. пример запроса для получения списка фильмов с сортировкой по году выпуска от раннего к позднему
```SQL
SELECT name, release_year
FROM movie
ORDER BY release_year;
```
2. пример запроса для получения списка фильмов в жанре "триллер" с рейтингом PG13
```SQL
SELECT m.name
FROM movie AS m
INNER JOIN film_genre AS fg ON m.film_id = fg.film_id
INNER JOIN genre AS g ON fg.genre_id = g.genre_id
WHERE g.name = 'thriller' AND m.rating = 'PG13'
ORDER BY m.name;
```
3. пример запроса для получения списка топ 10 фильмов с сортировкой по количеству лайков
```SQL
SELECT m.name, COUNT(l.user_id)
FROM movie AS m
LEFT OUTER JOIN likes AS l ON m.film_id = l.film_id
ORDER BY COUNT(l.user_id)
LIMIT 10;
```
4. пример запроса для получения списка друзей
```SQL
SELECT friend_id
FROM friends
WHERE user_id = '1' AND accepted = 'TRUE';
```
5. пример запроса для получения списка общих друзей
```SQL
SELECT f.friend_id
FROM (SELECT friend_id
      FROM friends
      WHERE user_id = '1' AND accepted ='TRUE') AS f
INNER JOIN (SELECT friend_id
            FROM friends
            WHERE user_id = '2' AND accepted = 'TRUE') AS f2 ON f.friend_id = f2.friend_id
ORDER BY f.friend_id;
```
