# Важно! PR сделал из main в форкнутую с вас ветку, т.к. забыл что изначально лучше было начинать в ветке, вмерживаемой вдальнейшем в main

Выделим абстракцию GameObject - это нечто что инкапсулирует свой собственный rectangle, текстуру и координаты.

От нее отнаследуем Tank и Tree как образцы таких игровых объектов. Tree практически не наделяется никакими дополнительными свойствами, однако Tank получает метод handleInput(), который реализован с использованием другой выделенной нами в целях избавления от копипаста абстракции - Direction. По сути это словарь из фиксированных структур - каждое направление можно наделить своим rotation, характерными дельтами по x, y координатам и привязанными к направлению кнопками. Пользуясь таким словарем мы избавляемся от копипаста в коде функции handleInput() у класса Tank - просто проверяем что была нажата та или иная кнопка в цикле, по нему определяем Direction, который определяет destinationCoordinates танка и его rotation.

Таким образом, применяя обсуждаемые на лекции принципы ООП и выделяя отдельные абстракции в иерархию нам удалось сделать метод render() в классе GameDesktopLauncher понятным и читаемым, а саму логику исполнения расширяемой для новых объектов, потенциально внедряемых в нашу игру.
