# Covid19_Map
전국 코로나 센터의 위치와 자신의 위치를 지도에 보여주는 어플리케이션입니다.본 프로젝트는 자신의 위치와 코로나 센터의 위치를 네이버 지도에 띄우고 선택한 코로나 센터의 정보를 간략히 볼 수 있는 기능을 가진 어플리케이션입니다. 공공데이터의 정보를 활용하였고 총 100개의 정보를 받아와 받아오는 정보의 개수를 측정하여 Progress Bar가 채워지는 기능까지 추가하였습니다. 지난 에어비앤비프로젝트 에서 경험했던 프로젝트에서 Hilt와 공공 API를 추가해보았습니다

### :wrench: 기능설명
+ ProgressBar API 데이터에 따라 채워지는 기능
+ API를 ROOM에 저장 기능
+ 네이버 지도 상성 및 저장된 데이터를 불러와 마커 생성
+ 센터 정보 창 구현

### 📜 깨달은 점
+ Retrofit을 활용하여 API를 받아오는 기능구현에 대한 이해도가 높아졌습니다.
+ Hilt 2.4.0으로 변경되면서 이전과 다른 설계 방식을 학습하였습니다.
+ Room과 같은 로컬데이터는 코루틴 방식으로 사용될 때 백 스레드에서만 동작되는 것을 알게 되었습니다.
+ 볼륨을 최대로 작게 하여 명확한 아키텍쳐 구조를 설계하였습니다.

### :lollipop: 완성 화면

<p align = center>
<img src = "https://user-images.githubusercontent.com/48902047/163483997-9a07ed56-6348-497c-9521-46c98d3e038f.jpg" width="20%" height="20%">
<img src = "https://user-images.githubusercontent.com/48902047/163483987-b23ea8c2-f069-4109-a193-847e5a53c9b1.jpg" width="20%" height="20%">
<img src = "https://user-images.githubusercontent.com/48902047/163483969-226570af-e232-4f1a-a35d-6ec0ec8986a9.jpg" width="20%" height="20%">
<img src = "https://user-images.githubusercontent.com/48902047/163483977-382c6b2e-0fcf-4a84-b4ca-68ab50224596.jpg" width="20%" height="20%">
</p>

### 기술 스택

#### 아키텍처 구조
<p align = center>
<img src = "https://user-images.githubusercontent.com/48902047/169705819-60894c04-25e1-4350-924d-832fd5e1fc23.png" width="50%" height="50%">
</p>

본 프로젝트의 구조는 UI레이어 – Data레이어 로만 설계되었습니다. UI Element(Activity - Xml)의 관계는 DataBinding을 활용하여 커플링을 끊기 위해 노력하였으며 Activity는 ViewModel에 LiveData를 통해서만 정보를 받아올 수 있게 하였습니다. 또한 ViewModel은 Repository를 통해서만 Data레이어(DataSource, Room)에 접근 할 수 있게 개발되었습니다.

#### Hilt
지난 ‘배달의 민족’ 프로젝트 당시 Hilt 2.3.0을 사용하였습니다. 이후 2.3.1 Hilt 사용에서는 가장 크게 바뀐 것은 ViewModel의 주입방식 이였습니다. 기존 방식은 lifecycle-viewmodel이란 라이브러리를 받아야만 ViewModel 주입이 가능 했으나 지금은 @HiltViewModel로 가능합니다.뿐만 아니라 @Assisted 없이도 SavedStateHandle 를 주입가능합니다.

<p align = center>
<img src = "https://user-images.githubusercontent.com/48902047/169705944-28435b03-fafe-471f-adf3-8bdc3a09a9d4.png" width="80%" height="80%"></br>
이전 코드
</p>

<p align = center>
<img src = "https://user-images.githubusercontent.com/48902047/169705951-572f2211-78e2-49a7-aab7-5c7ff683040e.png" width="80%" height="80%"></br>
바뀐 코드
</p>

또한 모듈 간의 설계의 이해도가 낮았으나 이번 프로젝트를 통해 Service – Retrofit – LocalDB 의 관계를 Hilt를 통하여 의존도를 끊어내는 방법을 학습하게 되었습니다. 아래는 이번 프로젝트의 모듈관의 관계를 도식화한 것입니다. 이번 프로젝트에서 사용되었던 API Module – Data Module – Local Module – Repository Module 간의 관계도 입니다.

<p align = center>
<img src = "https://user-images.githubusercontent.com/48902047/169705972-894f97a4-6887-45fe-b2f4-fcfc7d2d6776.png"></br>
</p>
