package controllers

import models.Employee
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import repositories.EmployeeRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class EmployeeController @Inject()(
                                val controllerComponents: ControllerComponents,
                                repo: EmployeeRepository
                              )(implicit ec: ExecutionContext) extends BaseController {


  def testListEmployees: Action[AnyContent] = Action.async {
    repo.listEmployees().map { employees =>
      if(employees.isEmpty) {
        Ok(Json.obj("message" -> "No employees found"))
      } else {
        Ok(Json.toJson(employees))
      }
    }
  }

  def createEmployee: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Employee].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid employee data"))),
      employee => {
        repo.createEmployee(employee).map { createdEmployee =>
          Created(Json.toJson(createdEmployee))
        }
      }
    )
  }

  def deleteEmployee(id: Long): Action[AnyContent] = Action.async {
    repo.deleteEmployee(id).map { deletedCount =>
      if(deletedCount > 0) {
        Ok(Json.obj("message" -> s"Employee $id deleted successfully"))
      } else {
        Ok(Json.obj("message" -> s"Employee $id not found"))
      }
    }
  }


}
