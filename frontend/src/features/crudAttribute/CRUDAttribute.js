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
import Snackbar from "@mui/material/Snackbar";

export default function ({id, name, description, options, refresh, setShowAlert}) {
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [displayModal, setDisplayModal] = useState(false);

  function deleteAttribute() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch(`http://localhost:8080/admin/attributes/${id}`, {
      headers, method: 'DELETE'
    })
    .then(() => {
      setShowAlert("Attribut supprimé");
      refresh();
    })
  }


  return <div className="CRUDAttribute">
    <Attribute id={id} name={name}
               description={description}
               options={options}/>
    {
        options.length === 0 && <Alert severity="warning">Cet attribut ne
          possède pas d'options</Alert>
    }
    <button className="btn styledButton"
            onClick={deleteAttribute}>Supprimer
    </button>
    <Link to={`/admin/attribute/${id}`}>
      <button className="btn styledButton">Modifier</button>
    </Link>
  </div>
}
