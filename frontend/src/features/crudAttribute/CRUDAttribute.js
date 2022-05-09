import Attribute from "../attribute/Attribute";
import {
  Alert,
  Button,
  Dialog, DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from "@mui/material";
import {useState} from "react";
import {useSelector} from "react-redux";
import {Link} from "react-router-dom";

export default function ({id, name, description, options, refresh}) {
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [displayModal, setDisplayModal] = useState(false);

  function deleteAttribute() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch(`http://localhost:8080/admin/attributes/${id}`, {
      headers, method: 'DELETE'
    })
    .then(() => refresh())
  }

  return <div className="CRUDAttribute">
    <Attribute id={id} name={name}
               description={description}
               options={options}/>
    {
        options.length === 0 && <Alert severity="warning">Cet attribut ne
          possède pas d'options</Alert>
    }
    <Button type="button" color="error" variant="outlined"
            onClick={deleteAttribute}>Supprimer</Button>
    <Link to={`/admin/attribute/${id}`}>
      <Button type="button" color="warning" variant="outlined"
              onClick={() => setDisplayModal(true)}>Modifier</Button>
    </Link>
  </div>
}
