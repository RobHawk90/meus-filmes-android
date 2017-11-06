# meus-filmes-android
Permite pesquisar um catálogo de filmes online e gerenciar a lista de favoritos offline.

<h2>Download APK</h2>

<p>
   Você pode instalar o app em seu dispositivo baixando o arquivo "app-meus-filmes.apk" na página inicial deste repositório.
</p>

<h2>Setup Inicial</h2>

<p>
  A chave da API <a href="https://www.themoviedb.org/documentation/api" target="_blank">https://www.themoviedb.org/documentation/api</a>
  foi ocultada - está configurada no .gitignore.
</p>

<p>
  É preciso implementar sua própria chave para poder debugar o app localmente. Segue o exemplo de implementação:
  /app/src/main/java/robhawk/com/br/meusfilmes/web/ApiKey.java
</p>

<pre>
package robhawk.com.br.meusfilmes.web;

public class ApiKey {
    public static final String PRIVATE_KEY = "sua_chave_da_api_aqui";
}
</pre>


