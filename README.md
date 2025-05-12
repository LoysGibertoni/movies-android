## Descrição
A ideia do projeto é bem simples: realizar a busca e exibição de detalhes de filmes usando uma API (https://www.omdbapi.com/).
A implementação foi feita usando as seguintes características:
- MVVM + Clean Architecture;
- Compose;
- Navigation;
- Material 3;
- Retrofit;
- Paging 3;
- Koin;
- Coil;
- JUnit + Mockk (Testes unitários);
- Espresso + MockServer próprio (Testes instrumentados);


## Execução
Para executar o projeto, é necessário:
- criar uma key através do link https://www.omdbapi.com/apikey.aspx;
- ativar a key pelo link de confirmação enviado via e-mail;
- adicionar a key a uma propriedade do `local.properties`: `API_KEY=<sua-key>`