package controllers

import javax.inject._
import play.api._
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json, OFormat, __}
import play.api.mvc._

import scala.collection.mutable.ListBuffer

case class Cart(id: Long, name: String, description: String)

object Cart {
  implicit val cartFormat: OFormat[Cart] = Json.format[Cart]
}

@Singleton
class CartController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val carts = ListBuffer(
    Cart(1, "Cart 1", "Description 1"),
    Cart(2, "Cart 2", "Description 2"),
    Cart(3, "Cart 3", "Description 3")
  )

  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(carts))
  }

  def getById(id: Long): Action[AnyContent] = Action {
    carts.find(_.id == id) match {
      case Some(cart) => Ok(Json.toJson(cart))
      case None => NotFound(Json.obj("error" -> "not found"))
    }
  }

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Cart] match {
      case JsSuccess(newCart, _) =>
        if (carts.exists(_.id == newCart.id)) {
          Conflict(Json.obj("error" -> "id already exists"))
        } else {
          carts += newCart
          Created(Json.toJson(newCart))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "invalid json"))
    }
  }

  def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Cart] match {
      case JsSuccess(updatedCart, _) =>
        carts.indexWhere(_.id == id) match {
          case -1 => NotFound(Json.obj("error" -> "not found"))
          case index =>
            carts(index) = updatedCart
            Ok(Json.toJson(updatedCart))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "invalid json"))
    }
  }

  def delete(id: Long): Action[AnyContent] = Action {
    carts.indexWhere(_.id == id) match {
      case -1 => NotFound(Json.obj("error" -> "not found"))
      case index =>
        carts.remove(index)
        Ok(Json.obj("message" -> "deleted"))
    }
  }

}
