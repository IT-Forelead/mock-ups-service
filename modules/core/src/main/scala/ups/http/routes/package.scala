package ups.http

import cats.effect.Async
import io.circe.{ Decoder, Encoder }
import org.http4s.{ EntityDecoder, EntityEncoder }
import org.http4s.circe.{ jsonEncoderOf, jsonOf }

package object routes {
  implicit def deriveEntityEncoder[F[_]: Async, A: Encoder]: EntityEncoder[F, A] = jsonEncoderOf[F, A]

  implicit def deriveEntityDecoder[F[_]: Async, A: Decoder]: EntityDecoder[F, A] = jsonOf[F, A]
}
