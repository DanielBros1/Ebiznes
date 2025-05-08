package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class ItemControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  val welcomeMessage = "Welcome to Play"
  val ContentTypeHtml = "text/html"

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new ItemController(stubControllerComponents())
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some(ContentTypeHtml)
      contentAsString(home) must include (welcomeMessage)
    }

    "render the index page from the application" in {
      val controller = inject[ItemController]
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some(ContentTypeHtml)
      contentAsString(home) must include (welcomeMessage)
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some(ContentTypeHtml)
      contentAsString(home) must include (welcomeMessage)
    }
  }
}
