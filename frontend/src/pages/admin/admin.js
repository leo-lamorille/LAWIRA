import './admin.scss';
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {
  Button,
  CircularProgress,
  Dialog, DialogActions,
  DialogContent, DialogContentText,
  DialogTitle
} from "@mui/material";
import Attribute from "../../features/attribute/Attribute";
import CRUDAttribute from "../../features/crudAttribute/CRUDAttribute";
import CreateAttributeForm
  from "../../features/form/createAttribute/createAttributeForm";

export default function Admin() {
  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [pendingCommands, setPendingCommands] = useState();
  const [attributes, setAttributes] = useState();

  function refreshPendingCommands() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/commands', {
      headers
    })
    .then(res => res.json())
    .then(res => setPendingCommands(res))
  }

  function refreshAttributes() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/attributes', {
      headers
    })
    .then(res => res.json())
    .then(res => setAttributes(res))
  }

  function validateCommand(id) {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/commands/' + id + '/validate', {
      headers, method: 'PUT'
    }).then(res => {
      if (res.status === 200) {
        refreshPendingCommands();
      } else {
        console.error(res.json());
      }
    })
  }

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    } else if (userRole !== 'ADMIN') {
      navigate('/home');
    }
    refreshPendingCommands()
    refreshAttributes();
  }, []);

  return <div className="admin__page">
    <h1>Commandes à traiter</h1>
    <div className="pendingCommands">
      {
          (pendingCommands && pendingCommands.map(
              ({id, username, quantity, options}) => {
                return <div key={id}>
                  {username} {id} {quantity}
                  <ul>
                    {
                      options.map(({
                        attributeId,
                        attributeName,
                        optionId,
                        optionValue,
                        optionType
                      }) => {
                        return <li
                            key={attributeId}>{attributeName}: {optionValue}</li>
                      })
                    }
                  </ul>
                  <button onClick={() => validateCommand(id)}>Valider</button>
                </div>
              })) || <CircularProgress/>
      }
    </div>

    <h1>Produit</h1>
    <div className="attributes">
      {
        (attributes && attributes.map(
                ({id, name, description, options}) => <CRUDAttribute key={id}
                                                                     id={id}
                                                                     name={name}
                                                                     description={description}
                                                                     options={options}
                                                                     refresh={refreshAttributes}/>)
            || <CircularProgress/>)
      }
      <CreateAttributeForm/>
    </div>


  </div>
}
