import './product.scss';
import {useEffect, useState} from "react";
import {Button, CircularProgress, TextField} from "@mui/material";
import Attribute from "../../features/attribute/Attribute";
import {useLocation, useNavigate} from "react-router-dom";
import {parseSearchRequest, selectionToLocation} from "../../util";
import {useSelector} from "react-redux";

export default function Product() {
  const [attributes, setAttributes] = useState([]);
  const [errorMessage, setErrorMessage] = useState("")
  const location = useLocation();
  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const [configurationName, setConfigurationName] = useState('');
  const requestParams = parseSearchRequest(location);
  const {commandId, configurationId} = requestParams;
  let {quantity} = requestParams;
  quantity = parseInt(quantity);
  quantity = isNaN(quantity) ? 0 : quantity;

  const selection = {}
  attributes.forEach(({id}) => {
    const optionId = requestParams[id];
    if (optionId !== undefined) {
      selection[id] = optionId
    }
  })

  useEffect(() => {
    fetch('http://localhost:8080/public/attributes')
    .then((res) => res.json())
    .then(res => setAttributes(res));
  }, [])

  useEffect(() => {
    if (commandId !== undefined) {
      const headers = new Headers()
      headers.append("Authorization", 'Bearer ' + userToken)
      headers.append("Content-Type", "application/json")
      fetch('http://localhost:8080/user/commands/' + commandId, {
        method: 'GET',
        headers
      })
      .then(res => {
        if (res.status !== 200) {
          const url = location.pathname + selectionToLocation(selection,
              quantity, undefined, configurationId);
          navigate(url);
        }
      })
    }
    if (configurationId !== undefined) {
      const headers = new Headers()
      headers.append("Authorization", 'Bearer ' + userToken)
      headers.append("Content-Type", "application/json")
      fetch('http://localhost:8080/user/configurations/' + configurationId, {
        method: 'GET',
        headers
      })
      .then(res => {
        if (res.status !== 200) {
          const url = location.pathname + selectionToLocation(selection,
              quantity, commandId, undefined);
          navigate(url);
        } else {
          return res.json();
        }
      }).then(res => {
        if (res) {
          const {name} = res;
          setConfigurationName(name);
        }
      })
    }
  })

  if (attributes.length === 0) {
    return <div className="product__page">
      <div className="progress">
        <CircularProgress style={{
          margin: "auto"
        }}/>
      </div>
    </div>;
  }

  function checkConnection() {
    if (userToken === '') {
      navigate('/account/signIn')
      return false;
    }
    return true;
  }

  function clickOnOption(attributeId, optionId) {
    const newSelection = Object.assign(selection);
    newSelection[attributeId] = optionId;
    const url = location.pathname + selectionToLocation(newSelection, quantity,
        commandId, configurationId);
    navigate(url);
  }

  function checkSelectionBeforeAddToStore() {
    const nbAttributes = attributes.length;
    const nbSelection = Object.entries(selection).length;
    if (nbSelection !== nbAttributes) {
      setErrorMessage("Erreur: vous avez oublié des configurations ...");
      return false;
    }
    if (quantity < 1) {
      setErrorMessage("Erreur: La quantité doit être supérieure à 1");
      return false;
    }
    setErrorMessage("")
    return true;
  }

  function checkSelectionBeforeAddToConfig() {
    const nbAttributes = attributes.length;
    const nbSelection = Object.entries(selection).length;
    if (nbSelection !== nbAttributes) {
      setErrorMessage("Erreur: vous avez oublié des configurations ...");
      return false;
    }
    setErrorMessage("")
    return true;
  }

  function saveConfiguration() {
    if (!checkConnection()) {
      return;
    }
    const isSelectionGood = checkSelectionBeforeAddToConfig()
    if (isSelectionGood) {
      const configName = prompt(
          "Choisissez un nom pour votre configuration");
      if (configName === undefined
          || configName === null
          || configName.length === 0) {
        setErrorMessage('Le nom de la configuration ne doit pas être vide');
        return;
      }

      const headers = new Headers()
      headers.append("Authorization", 'Bearer ' + userToken)
      headers.append("Content-Type", "application/json")
      const body = JSON.stringify({
        name: configName,
        options: Object.entries(selection).map(elt => elt[1])
      })
      fetch('http://localhost:8080/user/configurations', {
        method: 'POST',
        headers, body
      })
      .then((res) => {
        if (res.status === 201) {
          navigate("/account")
        } else {
          console.error(res.json());
        }
      })
    }
  }

  function updateConfiguration() {
    if (!checkConnection()) {
      return;
    }
    const isSelectionGood = checkSelectionBeforeAddToStore()
    if (isSelectionGood) {
      const configName = prompt(
          "Choisissez un nom pour votre configuration", configurationName);
      if (configName === undefined
          || configName === null
          || configName.length === 0) {
        setErrorMessage('Le nom de la configuration ne doit pas être vide');
        return;
      }
      const headers = new Headers()
      headers.append("Authorization", 'Bearer ' + userToken)
      headers.append("Content-Type", "application/json")
      const body = JSON.stringify({
        name: configName,
        options: Object.entries(selection).map(elt => elt[1])
      })
      fetch('http://localhost:8080/user/configurations/' + configurationId,
          {
            method: 'PUT',
            headers, body
          })
      .then((res) => {
        if (res.status === 200) {
          navigate("/account")
        } else {
          console.error(res.json());
        }
      })
    }
  }

  function saveCommand(event) {
    event.preventDefault();
    if (!checkConnection()) {
      return;
    }
    const isSelectionGood = checkSelectionBeforeAddToStore()
    if (isSelectionGood) {
      const headers = new Headers()
      headers.append("Authorization", 'Bearer ' + userToken)
      headers.append("Content-Type", "application/json")
      const body = JSON.stringify({
        quantity: quantity,
        options: Object.entries(selection).map(elt => elt[1])
      })
      fetch('http://localhost:8080/user/commands', {
        method: 'POST',
        headers, body
      })
      .then(res => {
        if (res.status === 201) {
          navigate('/basket');
        } else {
          console.error(res.json());
        }
      })
    }
  }

  function updateCommand() {
    if (!checkConnection()) {
      return;
    }
    const isSelectionGood = checkSelectionBeforeAddToStore()
    if (isSelectionGood) {
      const headers = new Headers()
      headers.append("Authorization", 'Bearer ' + userToken)
      headers.append("Content-Type", "application/json")
      const body = JSON.stringify({
        quantity: quantity,
        options: Object.entries(selection).map(elt => elt[1])
      })
      fetch('http://localhost:8080/user/commands/' + commandId, {
        method: 'PUT',
        headers, body
      })
      .then((res) => {
        if (res.status === 200) {
          navigate("/basket")
        } else {
          console.error(res.json());
        }
      })
    }
  }

  return <div className="product__page">
    <h1>PRODUIT</h1>
    <form onSubmit={saveCommand}>
      {
        attributes.map(
            ({id, name, description, options}) => <Attribute key={id} id={id}
                                                             name={name}
                                                             description={description}
                                                             options={options}
                                                             selectedOptionId={selection[id]}
                                                             clickOnOption={clickOnOption}/>)
      }

      <button className="btn styledButton" onClick={saveConfiguration}>
        Sauvegarder la configuration
      </button>
      {
          configurationId && <button className="btn styledButton"
                                     onClick={updateConfiguration}>
            Mettre à jour la configuration
          </button>
      }

      <TextField className="quantity" value={quantity} type="text"
                 label="Quantité"
                 focused onChange={(event) => {
        let newQuantity = parseInt(event.target.value)
        newQuantity = isNaN(newQuantity) ? 0 : newQuantity;
        const url = location.pathname + selectionToLocation(selection,
            newQuantity, commandId, configurationId);
        navigate(url);
      }}/>
      {
        <div className="errorMessage">{errorMessage}</div>
      }
      <button type="submit" className="btn styledButton">
        Ajouter au panier
      </button>
      {
          commandId && <button className="btn styledButton"
                               onClick={updateCommand}>
            Mettre à jour la commande
          </button>
      }
    </form>
  </div>
}
