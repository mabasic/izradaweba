package eu.izradaweba.mail.aws.v1

import eu.izradaweba.Config
import eu.izradaweba.pages.ContactMessage
import eu.izradaweba.pages.subjects
import eu.izradaweba.validation.Email

import com.amazonaws.services.simpleemailv2.*
import com.amazonaws.auth.{
  AWSCredentials,
  AWSStaticCredentialsProvider,
  BasicAWSCredentials
}
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.simpleemailv2.model.{
  Body,
  Content,
  Destination,
  Message,
  SendEmailRequest,
  EmailContent,
  SendEmailResult
}

def getClient(): AmazonSimpleEmailServiceV2 =
  val credentials = BasicAWSCredentials(
    Config.ses.key,
    Config.ses.secret
  )

  AmazonSimpleEmailServiceV2ClientBuilder
    .standard()
    .withCredentials(AWSStaticCredentialsProvider(credentials))
    .withRegion(Config.ses.region)
    .build

def send(
    to: String,
    subject0: String,
    body0: String,
    replyTo: Email
): SendEmailResult =
  val subject: Content = Content().withData(subject0)

  val bodyText: Content = Content().withData(body0)
  val body: Body = Body().withText(bodyText)

  val message: Message = Message().withBody(body).withSubject(subject)

  val content: EmailContent = EmailContent().withSimple(message)

  val destination: Destination =
    Destination().withToAddresses(to)

  val request: SendEmailRequest = SendEmailRequest()
    .withDestination(destination)
    .withContent(content)
    .withReplyToAddresses(replyTo)

  request.setFromEmailAddress(Config.replyToAddress)

  val client = getClient()

  client.sendEmail(request)

def sendContactMessage(message: ContactMessage): SendEmailResult =
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
