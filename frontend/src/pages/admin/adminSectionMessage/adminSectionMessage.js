import './adminSectionMessage.scss';
import {Link} from "react-router-dom";

export default function ({
  id,
  firstname,
  lastname,
  email,
  subject,
  content,
  status,
  account,
  sentAt
}) {
  function shortenMessage() {
    const lastReturn = content.indexOf('\n');
    let limit = content.length;
    if (lastReturn > 0 && lastReturn < 50) {
      limit = lastReturn;
    }
    if (content.length > 50) {
      limit = 50;
    }
    return content.substring(0, limit) + '...';
  }

  return <div className="adminSectionMessage">
    <Link to={"message/" + id}>
      {status === "NEW" ? <img
          alt="Enveloppe fermée"
          src="/envelope.png"
          style={{
            height: 75,
            width: 150,
            paddingTop: 50
          }}
      /> : <img
          alt="Enveloppe ouverte"
          src="/open-envelope.png"
          style={{
            height: 100,
            width: 150
          }}
      />}
    </Link>

    <div>
      <h3>{subject}</h3>
      <div><span>De:</span> {firstname} {lastname}</div>
      <div><span>Email:</span> {email}</div>
      <div>{shortenMessage()}</div>
    </div>
    <div>{sentAt}</div>
  </div>
}
