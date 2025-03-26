package controllers

import javax.inject._
import play.api._
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json, OFormat, __}
import play.api.mvc._

import scala.collection.mutable.ListBuffer

case class Category(id: Long, name: String, description: String)

object Category {
  implicit val categoryFormat: OFormat[Category] = Json.format[Category]
}

@Singleton
class CategoryController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val categories = ListBuffer(
    Category(1, "Category 1", "Description 1"),
    Category(2, "Category 2", "Description 2"),
    Category(3, "Category 3", "Description 3")
  )

  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(categories))
  }

  def getById(id: Long): Action[AnyContent] = Action {
    categories.find(_.id == id) match {
      case Some(category) => Ok(Json.toJson(category))
      case None => NotFound(Json.obj("error" -> "not found"))
    }
  }

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category] match {
      case JsSuccess(newCategory, _) =>
        if (categories.exists(_.id == newCategory.id)) {
          Conflict(Json.obj("error" -> "id already exists"))
        } else {
          categories += newCategory
          Created(Json.toJson(newCategory))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "invalid json"))
    }
  }
  
  def update(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category] match {
      case JsSuccess(updatedCategory, _) =>
        categories.indexWhere(_.id == id) match {
          case -1 => NotFound(Json.obj("error" -> "not found"))
          case index =>
            categories(index) = updatedCategory
            Ok(Json.toJson(updatedCategory))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "invalid json"))
    }
  }
  
  def delete (id: Long): Action[AnyContent] = Action {
    categories.indexWhere(_.id == id) match {
      case -1 => NotFound(Json.obj("error" -> "not found"))
      case index =>
        categories.remove(index)
        Ok(Json.obj("message" -> "deleted"))
    }
  }
}
