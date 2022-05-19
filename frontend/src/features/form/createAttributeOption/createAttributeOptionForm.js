import {useSelector} from "react-redux";
import {useState} from "react";
import {
  Button,
  Dialog, DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from "@mui/material";
import {useNavigate} from "react-router-dom";

export default function ({attributeId, refresh}) {
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const navigate = useNavigate();
  const [display, setDisplay] = useState(false)
  const [type, setType] = useState('TEXT')
  const [value, setValue] = useState('')

  function createAttributeOption(event) {
    event.preventDefault();
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    headers.append("Content-Type", "application/json")
    const body = JSON.stringify({
      type, value
    })
    fetch(`http://localhost:8080/admin/attributes/${attributeId}/options`, {
      headers, method: 'POST', body
    }).then(res => res.json())
    .then(res => {
      setDisplay(false);
      refresh();
    });
  }

  return <>
    <button type="button" className="btn styledButton"
            onClick={() => setDisplay(true)}>Nouvelle option</button>
    <Dialog open={display}>
      <form className="createAttributeForm" onSubmit={createAttributeOption}>
        <DialogTitle>Nouvel attribut</DialogTitle>
        <DialogContent>
          <DialogContentText>Créer une nouvelle option permettra à
            l'utilisateur d'avoir plus de choix pour la personnalisation de son
            produit
          </DialogContentText>
          <div className="input">
            <label htmlFor="attributeName">Type</label>
            <select value={type} onChange={(e) => setType(e.target.value)}>
              <option value={"TEXT"}>Texte</option>
              <option value={"COLOR"}>Couleur</option>
            </select>
          </div>
          <div className="input">
            <label htmlFor="attributeName">Valeur</label>
            <input value={value} onChange={e => setValue(e.target.value)}/>
          </div>
        </DialogContent>
        <DialogActions>
          <button type="button" className="btn styledButton"
                  onClick={() => setDisplay(
                      false)}>Annuler</button>
          <button type="submit" className="btn styledButton">
            Valider
          </button>
        </DialogActions>
      </form>
    </Dialog></>
}
