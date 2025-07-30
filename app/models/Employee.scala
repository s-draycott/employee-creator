package models

import play.api.libs.json.{Json, OFormat}

case class Employee (
  id: Option[Long] = None,
  firstName: String,
  lastName: String,
  email: String,
  mobileNumber: String,
  address: String,
  contracts: Seq[Contract] = Seq.empty
  )

//This creates a companion object for your Employee case class. A companion object is a special object in Scala that has the same name as a class or case class, and is used to define shared functionality for it.
/*
Part	What it means
implicit:	Makes this available automatically wherever JSON conversion is needed.
val employeeFormat: We're naming this value employeeFormat — it's a formatter.
: OFormat[Employee]	The type of the formatter. OFormat works for both reading and writing JSON.
= Json.format[Employee]	This uses Play’s macro to auto-generate the logic to convert between JSON and Employee, using the field names of the case class.
 */
object Employee {
  implicit val employeeFormat: OFormat[Employee] = Json.format[Employee]
}