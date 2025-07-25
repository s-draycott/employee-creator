package models

import play.api.libs.json.{Json, OFormat}

import java.time.LocalDate

case class Contract(
  id: Option[Long] = None,
  employeeId: Long,
  startDate: LocalDate,
  contractType: String,
  fullTime: Boolean,
  endDate: Option[LocalDate],
  hoursPerWeek: Int
)

object Contract {
  implicit val contractFormat: OFormat[Contract] = Json.format[Contract]
}

