# Covid19_Map

### :lollipop: 완성 화면

<p align = center>
<img src = "https://user-images.githubusercontent.com/48902047/163483997-9a07ed56-6348-497c-9521-46c98d3e038f.jpg" width="20%" height="20%">
<img src = "https://user-images.githubusercontent.com/48902047/163483987-b23ea8c2-f069-4109-a193-847e5a53c9b1.jpg" width="20%" height="20%">
<img src = "https://user-images.githubusercontent.com/48902047/163483969-226570af-e232-4f1a-a35d-6ec0ec8986a9.jpg" width="20%" height="20%">
<img src = "https://user-images.githubusercontent.com/48902047/163483977-382c6b2e-0fcf-4a84-b4ca-68ab50224596.jpg" width="20%" height="20%">
</p>

<p align = center>
<img src = "https://user-images.githubusercontent.com/48902047/163724418-70144831-f4f7-4327-adfb-e005a3920e2d.png">
</p>

### :lollipop: 명세서
#### 1. 활용 기술
  - Language : Kotlin
  - Design Pattern : MVVM
  - UI Layout : XML
  - Network : Retrofit2
  - Jetpack
    +  DataBinding
    +  ViewModel
    +  Room
- Naver Map
- 비동기 : Coroutine Flow

#### 2. 요구사항
**1. Splash**
+ ProgressBar - O
+ 2초에 걸쳐 100%가 되도록 로딩바 구현 - O
  + 단, API 데이터 저장이 완료되지 않았다면 80%에서 대기 - O
  + 저장이 완료되면 0.7초에 걸쳐 100%를 만든 후 Map 화면으로 이동 - O
+ API를 통해 1페이지(page)에 10개(perPage) 씩 순서대로 10개 페이지 호출(총 100개)하여 데이터 저장 - O
+ ROOM
+ 저장이 완료되면 Map 화면으로 이동
**2. Map**
+ 마커 생성
  + 저장된 리스트의 데이터를 통해 마커 생성 - O
  + centerType에 따라 마커 색상 구분 - O
+ 마커 클릭
  + 해당 마커로 지도 카메라 이동 - O
  + 해당 마커의 정보를 정보안내창에 표시 - O
  + 선택된 상태에서 같은 마커를 다시 선택하는 경우 선택 해제 - O
+ 정보안내창
  + Visibility (마커가 선택된 경우 visible, 아닌 경우 gone) - O
  + 선택된 마커의 정보(Response Data)를 표시 - O
  + DataBinding - X
+ 표시 데이터 - O
  + address
  + centerName
  + facilityName
  + phoneNumber
  + updatedAt
+ 현재 위치 버튼
  + 버튼 클릭 시 현재 위치로 이동(Map Library 내 UI가 아닌 직접 버튼 생성) - X
