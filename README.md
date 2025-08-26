# AILad
Приложение для взаимодействия с LLM-моделью с поддержкой шаблонов RAG. Пользователь может выбрать роль/место, которые отправляются нейросети как часть запроса.

## Функционал
* Отправка запросов к LLM-модели с возможностью ввода текста.
* Использование шаблонов RAG для автоматического заполнения контекста (роль, окружение).
* Возможность создавать, редактировать и удалять шаблоны RAG.
* Просмотр истории запросов и ответов.
* Поиск по истории запросов по ключевым словам или параметрам.
* Сортировка истории запросов по дате, популярности или другим критериям.
* Сохранение избранных запросов и шаблонов для быстрого доступа.
* Работа с историей запросов в оффлайн-режиме (кэширование данных).
* Сохранение, редактирование и удобное использование запросов.
* Уведомление пользователя зайти в приложение, реализованы через Firebase.
* Локализация на английский и русский.
  
## Архитектура
В приложении используется упрощенная Clean Architecture для внутренней логики и MVVM для интерфейса. 
Из-за ограниченного времени реализации и отсутствии сложной логики (данные передаются из слоя данных в слой интерфейса почти без изменений) было принято решение не реализовывать UseCase'ы в слое домена, чтобы привело бы только к излишнему усложнению кода. Вместо этого, ViewModel напрямую использует репозитории с данными.  

Данное сочетание архитектур является широко распространенным в современной разработке. Кроме того, MVVM отлично подходит для разработки на Jetpack Compose, который используется для отрисовки интерфейса.

## Запуск
Скачайте apk с последнего релиза или соберите проект в Android Studio. 

minSdk = 29

targetSdk = 35.

## Скриншоты

<p float="left">
  <img src="https://sun9-52.userapi.com/impg/a2LDvyFcsTuRvlDVneND19VsRqzK7t4Bd_uuAg/BBsG6t1DZro.jpg?size=997x2160&quality=95&sign=5d0904e97a0b5277a2ef4a4b46e17c9f&type=album" width="250"/>
  <img src="https://sun9-26.userapi.com/impg/vasvH3mTGCSUR6ZjjgiyKRBOzkY5CFxzwRe2AA/IuaPM5F94wY.jpg?size=997x2160&quality=95&sign=5334d14c8fc38e9f7d7064d3c541e84b&type=album" width="250"/>
  <img src="https://sun9-74.userapi.com/s/v1/ig2/h6A6HjK8WOprQXMiiMyTYx7KTkkIrfTumYpfsae88U6YRsml3Yyb6j-8GZml4EOEbEZsxjuSDMbZrCfZXLY5095A.jpg?quality=95&as=32x69,48x104,72x156,108x234,160x347,240x520,360x780,480x1040,540x1170,640x1387,720x1560,1080x2340&from=bu&u=yJkos2iH8KV1ZU-GNDrZltbGcTnMci0DjbUt3zvv7qQ&cs=997x2160" width="250"/>
</p>

 <p float="left">
  <img src="https://sun9-55.userapi.com/impg/rOnbtKcP1TBikrzrRKMsCTYZMOxi5IMl3ycM_Q/6eNCorEWwPY.jpg?size=997x2160&quality=95&sign=5995c76b80f86726dcff0408c560b3e9&type=album" width="250"/>
  <img src="https://sun9-37.userapi.com/impg/mjJP54KkDRcQjPrLEonzJskHJnqykyYw8zpyYA/tx_ZI8Qkp1I.jpg?size=997x2160&quality=95&sign=e9e166ff682721e297eb10c4f00400c8&type=album" width="250"/>
  <img src="https://sun9-58.userapi.com/impg/jSmhcuU2h96gPJ35Q6AfHYYiPm1W-DRmFC8bow/KmYztTSJJc4.jpg?size=997x2160&quality=95&sign=e082de9945a21ae604322478871f5034&type=album" width="250"/>
</p>

<p float="left">
  <img src="https://sun9-73.userapi.com/impg/lkw4Q-yAzdJcsnkMU7GMZRMOB7QJmL062WJ6bQ/I9S9ciOSMfY.jpg?size=997x2160&quality=95&sign=741d5a4218cc00270489b11dded5685f&type=album" width="250"/>
  <img src="https://sun9-77.userapi.com/impg/X9YEeNIPkQYpVI_vu48o508VXwc48QP1R89-aQ/Rjgd4GDPEXI.jpg?size=997x2160&quality=95&sign=cb66e74b1bbf0efdca47ab459862c81e&type=album" width="250"/>
  <img src="https://sun9-47.userapi.com/impg/mRYuT15HuQbgOBPYxis0-ruhH7jp3amj4YiTpQ/oAaP54um880.jpg?size=997x2160&quality=95&sign=7ef6765063d6c187c02709f9888c264d&type=album" width="250"/>
</p>
