package tables

import slick.jdbc.MySQLProfile.api._
import models.Contract
import java.time.LocalDate
import models.Employee

class ContractTable(tag:Tag) extends Table[Contract](tag, "contract") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def employeeId = column[Long]("employee_id")
  def startDate = column[LocalDate]("start_date")
  def contractType = column[String]("contract_type")
  def fullTime = column[Boolean]("full_time")
  def endDate = column[Option[LocalDate]]("end_date")
  def hoursPerWeek = column[Int]("hours_per_week")

  def employeeFK = foreignKey("fk_employee", employeeId, TableQuery[EmployeeTable])(_.id, onDelete = ForeignKeyAction.Cascade)

  def * = (id.?, employeeId, startDate, contractType, fullTime, endDate, hoursPerWeek) <> ((Contract.apply _).tupled, Contract.unapply)
}
