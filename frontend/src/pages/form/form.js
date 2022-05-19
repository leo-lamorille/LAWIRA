import './form.scss';
import {useRef, useState} from "react";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import Snackbar from '@mui/material/Snackbar';
import {Alert} from "@mui/material";

export default function Form() {
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [content, setContent] = useState('');
  const [subject, setSubject] = useState('');

  const [showAlert, setShowAlert] = useState(false);

  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const navigate = useNavigate();

  function sendMessage() {
    const headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    headers.append("Content-Type", 'application/json');
    const body = JSON.stringify({
      firstname,
      lastname,
      email,
      subject,
      content
    })
    fetch("http://localhost:8080/public/contact", {
      method: 'POST', body, headers
    }).then(res => {
      setFirstname("");
      setLastName("");
      setEmail("");
      setSubject("");
      setContent("");
      setShowAlert(true);
    }).catch(err => {
      console.error(err);
    })
  }

  const submit = (e) => {
    e.preventDefault();
    sendMessage();
  }

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setShowAlert(false);
  };

  return (
      <div className="formContainer" key="formContainer">
        <h1>Nous contacter</h1>
        <form className="formulaire" onSubmit={submit}>
          <div className="field-wrap">
            <label className="label" htmlFor="familyName">
              Nom<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="familyName" required autoComplete="off"
                   value={lastname}
                   onChange={(e) => setLastName(e.target.value)}/>
          </div>
          <div className="field-wrap">
            <label className="label" htmlFor="firstname">
              Prénom<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="firstname" required autoComplete="off"
                   value={firstname}
                   onChange={(e) => setFirstname(e.target.value)}/>
          </div>
          <div className="field-wrap">
            <label className="label" htmlFor="email">
              Em@il<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="email" required autoComplete="off"
                   value={email} onChange={(e) => setEmail(e.target.value)}/>
          </div>
          <div className="field-wrap content">
            <label className="label" htmlFor="subject">
              Sujet<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="subject" required autoComplete="off"
                   value={subject}
                   onChange={(e) => setSubject(e.target.value)}/>
          </div>
          <div className="field-wrap content">
            <label className="label">
              Veuillez expliquer votre situation<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <textarea required autoComplete="off" value={content}
                      onChange={(e) => setContent(e.target.value)}/>
          </div>
          <div className="field-wrap">
            <button type="submit"
                    className="btn styledButton validate">Envoyer
            </button>
          </div>
        </form>

        <Snackbar open={showAlert} autoHideDuration={6000}
                  onClose={handleClose}>
          <Alert severity="success" onClose={() => setShowAlert(false)}>
            Message envoyé, une réponse vous sera envoyée par mail
          </Alert>
        </Snackbar>
      </div>
  );
}
