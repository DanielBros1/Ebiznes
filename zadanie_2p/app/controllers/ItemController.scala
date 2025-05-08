package controllers

import javax.inject._
import play.api._
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json, OFormat, __}
import play.api.mvc._

import scala.collection.mutable.ListBuffer

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

case class Item(id: Long, name: String, description: String)

object Item {
  implicit val itemFormat: OFormat[Item] = Json.format[Item]
}

@Singleton
class ItemController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  private val items = ListBuffer(
    Item(1, "Item 1", "Description 1"),
    Item(2, "Item 2", "Description 2"),
    Item(3, "Item 3", "Description 3")
  )

  private val NotFoundMessage = "not found"


  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(items))
  }

  def getById(id: Long): Action[AnyContent] = Action {
    items.find(_.id == id) match {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound(Json.obj("error" -> NotFoundMessage))
    }
  }

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Item] match {
      case JsSuccess(newItem, _) =>
        if (items.exists(_.id == newItem.id)) {
          Conflict(Json.obj("error" -> "id already exists"))
        } else {
          items += newItem
          Created(Json.toJson(newItem))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "invalid json"))
    }
  }

  def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Item] match {
      case JsSuccess(updatedItem, _) =>
        items.indexWhere(_.id == id) match {
          case -1 => NotFound(Json.obj("error" -> NotFoundMessage))
          case i =>
            items.update(i, updatedItem)
            Ok(Json.toJson(updatedItem))
        }
    }
  }
  def delete(id: Long): Action[AnyContent] = Action {
    items.indexWhere(_.id == id) match {
      case -1 => NotFound(Json.obj("error" -> NotFoundMessage))
      case i =>
        items.remove(i)
        NoContent
    }
  }

   def index: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
     Ok(views.html.index())
   }

}