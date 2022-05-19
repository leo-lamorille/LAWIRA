import './adminAttribute.scss';
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import {Alert, Button, CircularProgress, MenuItem, Select} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import CRUDAttributeOption
  from "../../features/crudAttributeOption/CRUDAttributeOption";
import CreateAttributeOptionForm
  from "../../features/form/createAttributeOption/createAttributeOptionForm";
import {userSlice} from "../../features/slices/userSlice";
import {useCookies} from "react-cookie";
import Snackbar from "@mui/material/Snackbar";

export default function () {
  const userAction = userSlice.actions;
  const dispatch = useDispatch();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const navigate = useNavigate();

  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [loading, setLoading] = useState(true);
  const [initialAttribute, setInitialAttribute] = useState();

  const [showAlert, setShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");

  const [name, setName] = useState('')
  const [description, setDescription] = useState('')
  const [options, setOptions] = useState([])
  const {attributeId} = useParams();

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setShowAlert(false);
  };

  const logout = () => {
    dispatch(userAction.logout(undefined));
    removeCookie('token', {path: '/'});
    navigate('/home');
  }

  function refreshAttribute() {
    setLoading(true);
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch(`http://localhost:8080/admin/attributes/${attributeId}`, {
      headers, method: 'GET'
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 404) {
        navigate('/admin')
      } else if (res.status === 200) {
        return res.json();
      }
    })
    .then((attribute) => {
      setInitialAttribute(attribute);
      const {id, name, description, options} = attribute;
      setName(name);
      setLoading(false);
      setDescription(description);
      setOptions(options);

    });
  }

  function updateAttribute() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    headers.append("Content-Type", 'application/json');
    const body = JSON.stringify({
      name, description
    })
    fetch(`http://localhost:8080/admin/attributes/${attributeId}`, {
      headers, method: 'PUT', body
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refreshAttribute();

        setShowAlert(true);
        setAlertMessage("Attribut mis à jour");
      }
    });
  }

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    } else if (userRole !== 'ADMIN') {
      navigate('/home');
    }
    refreshAttribute();
  }, [])

  function checkIfAttributeModified() {
    const {
      name: attributeName,
      description: attributeDescription
    } = initialAttribute;
    return name !== attributeName || description !== attributeDescription;
  }

  function submit(event) {
    event.preventDefault();
    if (checkIfAttributeModified()) {
      updateAttribute()
    }
  }

  return <div className="adminAttribute">
    <Snackbar open={showAlert} autoHideDuration={3000}
              onClose={handleClose}>
      <Alert severity="success" onClose={() => setShowAlert(false)}>
        {alertMessage}
      </Alert>
    </Snackbar>
    <h1>Attribut {attributeId}</h1>
    {loading && <CircularProgress/> || <form onSubmit={submit}>
      <div>
        <label>Nom de l'attribut: </label>
        <input type="text" defaultValue={name}
               onChange={(e) => setName(e.target.value)}/>
      </div>
      <div>
        <label>Description </label>
        <textarea value={description}
                  onChange={(e) => setDescription(e.target.value)}/>
      </div>

      <button type="submit" className="btn styledButton"
              disabled={!checkIfAttributeModified()}>
        Valider la modification</button>
    </form>}

    <h2>Options</h2>
    {
      options.length === 0 ? <Alert severity="warning">Cet attribut ne
        possède pas d'options</Alert> : <table>
        <thead>
        <tr>
          <th>Type</th>
          <th>Valeur</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        {
          options.map(({id, type, value}) => {
            return <CRUDAttributeOption key={id} attributeId={attributeId}
                                        id={id}
                                        type={type} value={value}
                                        showAlert={(message)=>{
                                          setShowAlert(true);
                                          setAlertMessage(message);
                                        }}
                                        refresh={refreshAttribute}/>
          })
        }
        </tbody>
      </table>
    }
    <CreateAttributeOptionForm attributeId={attributeId}
                               refresh={refreshAttribute}/>

  </div>
}
