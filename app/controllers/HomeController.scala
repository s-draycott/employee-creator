package controllers

import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites


import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import repositories.EmployeeRepository
import repositories.ContractRepository
import models.Contract
import models.Employee

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(
  val controllerComponents: ControllerComponents,
  repo: EmployeeRepository
  )(implicit ec: ExecutionContext) extends BaseController {

  def index: Action[AnyContent] = Action {
    Ok(Json.obj("message" -> "Hello, Play + Slick + MySQL (Scala 2.13)!"))
  }

}
