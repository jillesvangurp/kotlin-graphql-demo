import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.pre
import org.w3c.dom.HTMLDivElement
import org.w3c.fetch.RequestInit
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.appendText
import kotlin.js.json


fun main() {

    val rootEl = document.getElementById("root_el") as HTMLDivElement
    val gqlRequest = document.create.pre { +"-" }
    val gqlResponse = document.create.pre { +"-" }

    // fugly UI
    rootEl.append(
        document.create.h1 { +"Graphql Request" },
        gqlRequest,
        document.create.h1 { +"Graphql Response" },
        gqlResponse,
        document.create.h1 { +"Leftpad demo" },
        document.createElement("p").appendText("I can has leftPad in kotlin '${leftPad("Foo", 50, "*")}'")
    )

    // print to the console!
    val requestJson = JSON.stringify(json().apply {
        this["operationName"] = null
        this["variables"] = json()
        this["query"] = "{ coders { name }}"
    }, null, 2)

    println(requestJson)
    gqlRequest.textContent = requestJson

    val req = RequestInit(method = "POST", headers = json().apply {
        this["Content-Type"] = "application/json"
    }, body = requestJson)


// without co-routines
//    window.fetch("http://localhost:8081/graphql",req).then { res ->
//        res.text().then {
//            console.log(it)
//            gqlResponse.textContent = JSON.stringify(JSON.parse(it),null,2)
//        }.catch {
//            println("OOPSIE ${it.message}")
//        }
//    }

    // lers use co-routines
    GlobalScope.async {
        val text = fetchGraphql(req)
        console.log(text)
        gqlResponse.textContent = JSON.stringify(JSON.parse(text), null, 2)
    }
}

private suspend fun fetchGraphql(req: RequestInit): String = try {
    val response = window.fetch("http://localhost:8081/graphql", req).await()
    response.text().await()
} catch (e: Exception) {
    println("OOPSIE ${e.message}")
    "${e.message}"
}
