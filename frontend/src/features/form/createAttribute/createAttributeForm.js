import './createAttributeForm.scss'
import {
  Button, Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from "@mui/material";
import {useState} from "react";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";

export default function () {
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [display, setDisplay] = useState(false)
  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const navigate = useNavigate();

  function createAttribute(event) {
    event.preventDefault();
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    headers.append("Content-Type", "application/json")
    const body = JSON.stringify({
      name, description
    })
    fetch('http://localhost:8080/admin/attributes', {
      headers, method: 'POST', body
    }).then(res => res.json())
    .then(res=>navigate(`/admin/attribute/${res.id}`));
  }

  return <>      <Button type="button" color="success" variant="outlined"
                         onClick={() => setDisplay(true)}>Nouvel
    Attribut</Button><Dialog open={display}>
    <form className="createAttributeForm" onSubmit={createAttribute}>
      <DialogTitle>Nouvel attribut</DialogTitle>
      <DialogContent>
        <DialogContentText>Créer un nouvel attribut permettra aux utilisateurs
          de personnaliser leur produit avec plus de choix</DialogContentText>
        <div className="input">
          <label htmlFor="attributeName">Nom de l'attribut</label>
          <input type="text" name="attributeName" value={name}
                 onChange={event => setName(event.target.value)}/>
        </div>
        <div className="input">
          <label htmlFor="attributeName">Description</label>
          <textarea value={description}
                    onChange={event => setDescription(event.target.value)}/>
        </div>
      </DialogContent>
      <DialogActions>
        <Button type="button"
                onClick={() => setDisplay(
                    false)}>Annuler</Button>

        <Button type="submit">
          Valider
        </Button>
      </DialogActions>
    </form>
  </Dialog></>
}
