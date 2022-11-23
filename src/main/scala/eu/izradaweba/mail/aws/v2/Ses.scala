package eu.izradaweba.mail.aws.v2

import eu.izradaweba.Config
import eu.izradaweba.pages.ContactMessage
import eu.izradaweba.pages.subjects
import eu.izradaweba.validation.Email
import software.amazon.awssdk.auth.credentials.{
  AwsCredentialsProvider,
  StaticCredentialsProvider,
  AwsBasicCredentials
}
import software.amazon.awssdk.services.sesv2.SesV2Client
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sesv2.model.{
  SendEmailRequest,
  SendEmailResponse,
  Content,
  Message,
  EmailContent,
  Destination,
  Body
}

val credentials = AwsBasicCredentials.create(
  Config.ses.key,
  Config.ses.secret
)

val region = Region.of(Config.ses.region)

val client = SesV2Client.builder
  .region(region)
  .credentialsProvider(StaticCredentialsProvider.create(credentials))
  .build

def send(
    to: String,
    subject0: String,
    body0: String,
    replyTo: Email
): SendEmailResponse =
  val subject: Content = Content.builder.data(subject0).build

  val bodyText: Content = Content.builder.data(body0).build
  val body: Body = Body.builder.text(bodyText).build

  val message: Message = Message.builder.body(body).subject(subject).build

  val content: EmailContent = EmailContent.builder.simple(message).build

  val destination: Destination =
    Destination.builder.toAddresses(to).build

  val request: SendEmailRequest = SendEmailRequest.builder
    .destination(destination)
    .content(content)
    .fromEmailAddress(Config.emailAddress)
    .replyToAddresses(replyTo)
    .build

  client.sendEmail(request)

def sendContactMessage(message: ContactMessage): SendEmailResponse =
  val emailSubject = "Poruka sa web stranice | IzradaWeba"

  val subject = subjects.find(_.id == message.subject.tag) match
    case Some(subject) => subject.text
    case None          => message.subject.tag

  val body = s"""Ime i prezime: ${message.full_name}
                |Email: ${message.email_address}
                |Predmet: ${subject}
                |Poruka:
                |
                |${message.message}
                |
                |*Korisnik je dao svoju privolu da ga se može kontaktirati u vezi ove poruke.""".stripMargin

  send(
    to = Config.emailAddress,
    subject0 = emailSubject,
    body0 = body,
    replyTo = message.email_address
  )
