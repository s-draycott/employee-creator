package tables

import slick.jdbc.MySQLProfile.api._
import models.Employee

class EmployeeTable(tag: Tag) extends Table[Employee](tag, "employee") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def email = column[String]("email")
  def mobileNumber = column[String]("mobile_number")
  def address = column[String]("address")

  // 👇 Mapping columns to the case class
  def * =
    (id.?, firstName, lastName, email, mobileNumber, address) <> (
      { case (id, fn, ln, em, mob, addr) =>
        Employee(id, fn, ln, em, mob, addr) // contracts defaults to Seq.empty
      },
      (e: Employee) => Some((e.id, e.firstName, e.lastName, e.email, e.mobileNumber, e.address))
    )
}
