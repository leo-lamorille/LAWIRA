import './adminAttribute.scss';
import {useParams} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import {Button, CircularProgress, MenuItem, Select} from "@mui/material";
import {useSelector} from "react-redux";
import CRUDAttributeOption
  from "../../features/crudAttributeOption/CRUDAttributeOption";
import CreateAttributeOptionForm
  from "../../features/form/createAttributeOption/createAttributeOptionForm";

export default function () {
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [loading, setLoading] = useState(true);
  const [initialAttribute, setInitialAttribute] = useState();

  const [name, setName] = useState('')
  const [description, setDescription] = useState('')
  const [options, setOptions] = useState([])
  const {attributeId} = useParams();

  function refreshAttribute() {
    setLoading(true);
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch(`http://localhost:8080/admin/attributes/${attributeId}`, {
      headers, method: 'GET'
    }).then(res => res.json())
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
    }).then(res => res.json())
    .then((attribute) => {
      refreshAttribute();
    });
  }

  useEffect(() => {
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

      <Button type="submit" variant="outlined" color="warning"
              disabled={!checkIfAttributeModified()}>
        Valider la modification</Button>
    </form>}

    <h2>Options</h2>
    <table>
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
          return <CRUDAttributeOption key={id} attributeId={attributeId} id={id}
                                      type={type} value={value}
                                      refresh={refreshAttribute}/>
        })
      }
      </tbody>
    </table>
    <CreateAttributeOptionForm attributeId={attributeId} refresh={refreshAttribute}/>

  </div>
}
