import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.p
import kotlinx.html.pre
import org.w3c.dom.HTMLDivElement
import org.w3c.fetch.RequestInit
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.json


suspend fun main() {

    val rootEl = document.getElementById("root_el") as HTMLDivElement
    document.create.apply {
        val gqlRequest = pre { +"-" }
        val gqlResponse = pre { +"-" }

        // fugly UI
        rootEl.append(
            h1 { +"Graphql Request" },
            gqlRequest,
            h1 { +"Graphql Response" },
            gqlResponse,
            h1 { +"Leftpad demo" },
            p { +"I can has leftPad in kotlin '${leftPad("Foo", 50, "*")}'" }
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

        // lets use co-routines
        // we marked main as suspend so we have a coroutine scope
        coroutineScope {
            async {
                // artificially delay
                (5 downTo 1).forEach {
                    gqlResponse.textContent = "in $it ..."
                    delay(1000)
                }
                val text = fetchGraphql(req)
                console.log(text)
                gqlResponse.textContent = JSON.stringify(JSON.parse(text), null, 2)
            }
        }
    }

}

private suspend fun fetchGraphql(req: RequestInit): String = try {
    val response = window.fetch("http://localhost:8081/graphql", req).await()
    response.text().await()
} catch (e: Exception) {
    // technically not needed but lets catch it here
    println("OOPSIE ${e.message}")
    "${e.message}"
}
