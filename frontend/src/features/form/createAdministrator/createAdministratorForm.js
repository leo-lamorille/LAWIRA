import {useSelector} from "react-redux";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {
  Dialog, DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from "@mui/material";

export default function ({refresh}) {
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [display, setDisplay] = useState(false)
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  function createAdministrator(event) {
    event.preventDefault();
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    headers.append("Content-Type", "application/json")
    const body = JSON.stringify({
      username, password
    })
    fetch('http://localhost:8080/admin/accounts', {
      headers, method: 'POST', body
    }).then(res => {
      if (res.status === 201) {
        refresh();
        setDisplay(false);
        setUsername("")
        setPassword("");
        setErrorMessage("")
      } else if (res.status === 409) {
        setErrorMessage("Le nom d'utilisateur existe déjà")
      }
    });
  }

  return <>
    <button type="button" className="btn styledButton"
            onClick={() => setDisplay(true)}>
      Nouvel Administrateur
    </button>
    <Dialog open={display}>
      <form className="createAttributeForm" onSubmit={createAdministrator}>
        <DialogTitle>Nouvel Administrateur</DialogTitle>
        <DialogContent>
          <DialogContentText>Créer un nouvel administrateur vous permettra de
            partager les droits d'administrations, prenez soin de ne donner les
            accès à des personnes de confiance uniquement</DialogContentText>
          <div className="input">
            <label htmlFor="username">Username</label>
            <input type="text" name="username" value={username}
                   onChange={event => setUsername(event.target.value)}
                   required/>
            <div style={{color: "red"}}>
              {errorMessage}
            </div>
          </div>
          <div className="input">
            <label htmlFor="password">Mot de passe</label>
            <input type="password" name="password" value={password}
                   onChange={event => setPassword(event.target.value)}
                   required/>
          </div>

        </DialogContent>
        <DialogActions>
          <button type="button" className="btn styledButton"
                  onClick={() => setDisplay(
                      false)}>Annuler
          </button>

          <button type="submit" className="btn styledButton">
            Valider
          </button>
        </DialogActions>
      </form>
    </Dialog></>
}
