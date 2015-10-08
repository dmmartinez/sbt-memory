package error

import org.joda.time._
import org.joda.time.format._
//import org.joda.convert._

import scala.pickling._, Defaults._
import scalaz._

case class Data4(val1: Option[String], val2: String)
case class Data5(val1: Option[String], val2: String)

case class Data3(val1: Option[String], val2: String, val val3: Option[String], val4: Option[String])

case class Data6(val1: Option[String], val2: String)

case class Data2(val data3: Option[Data3])

case class Data(
  data3: Option[Data3],
  data4: Option[Data4],
  data5: Option[Data5],
  ld1: Option[LocalDate],
  ld2: Option[LocalDate],
  ld3: Option[LocalDate],
  ld4: Option[LocalDate],
  ld5: Option[LocalDate],
  bool1: Option[Boolean],
  bool2: Option[Boolean],
  bool3: Option[Boolean],
  bool4: Option[Boolean],
  bool5: Option[Boolean],
  bool6: Option[Boolean],
  bool7: Option[Boolean],
  bool8: Option[Boolean],
  str1: Option[String],
  str2: Option[String],
  str3: Option[String],
  str4: Option[String],
  str5: Option[String],
  str6: Option[String],
  str7: Option[String],
  str8: Option[String],
  str9: Option[String],
  str10: Option[String],
  str11: Option[String],

  vdata2: Vector[Data2]
                 )

object Data {

  private val ldfmt = DateTimeFormat.forPattern("dd-MM-yyyy")

  class LocalDatePickler(implicit val format: PickleFormat) extends Pickler[LocalDate] with Unpickler[LocalDate] {

    private val stringUnpickler = implicitly[Unpickler[String]]

    override def pickle(picklee: LocalDate, builder: PBuilder): Unit = {
      builder.beginEntry(picklee)
      builder.putField("iso8601", b =>
        b.hintTag(FastTypeTag.String).beginEntry(ldfmt.print(picklee)).endEntry)
      builder.endEntry
    }

    override def unpickle(tag: String, reader: PReader): Any = {
      val dateReader = reader.readField("iso8601")
      val tag = dateReader.beginEntry
      val value = stringUnpickler.unpickle(tag, dateReader).asInstanceOf[String]
      dateReader.endEntry()
      LocalDate.parse(value, ldfmt)
    }

    override def tag = FastTypeTag[LocalDate]
  }

}
