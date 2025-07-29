package controllers

import repositories.ContractRepository
import models.Contract
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

import scala.concurrent.ExecutionContext

class ContractController @Inject()(
                                  val controllerComponents: ControllerComponents,
                                  repo: ContractRepository
                                  )(implicit ec: ExecutionContext) extends BaseController {


  //Ok(Json...) constructs a HTTP 200 OK response
  def testListContracts: Action[AnyContent] = Action.async {
    repo.listContracts().map { contracts =>
      if(contracts.isEmpty) {
        Ok(Json.obj("message" -> "No contracts found"))
      } else {
        Ok(Json.toJson(contracts))
      }
    }
  }

  def createContract: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Contract].fold(
    errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid contract data"))),
      contract => {
        repo.createContract(contract).map { createdContract =>
          Created(Json.toJson(createdContract))
        }
      }
  )}

  def deleteContract(id: Long): Action[AnyContent] = Action.async {
  repo.deleteContract(id).map { deletedCount =>
    if(deletedCount > 0) {
      Ok(Json.obj("message" -> s"Contract $id for employee  has been deleted successfully"))
    } else {
      Ok(Json.obj("message" -> s"Contract $id not found"))
    }
  }}

  def getContractsByEmployee(employeeId: Long): Action[AnyContent] = Action.async {
    repo.findContractsByEmployeeId(employeeId).map { contracts =>
      Ok(Json.toJson(contracts))
    }
  }
}
