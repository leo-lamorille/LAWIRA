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

  return <div className="product__page">
    <h1>PRODUIT</h1>
    {
      attributes.map(
          ({id, name, description, options}) => <Attribute key={id} id={id}
                                                           name={name}
                                                           description={description}
                                                           options={options}
                                                           selectedOptionId={selection[id]}
                                                           clickOnOption={clickOnOption}/>)
    }

    <Button variant="outlined" onClick={() => {
      if (!checkConnection()) {
        return;
      }
      const isSelectionGood = checkSelectionBeforeAddToStore()
      if (isSelectionGood) {
        const configurationName = prompt(
            "Choisissez un nom pour votre configuration");
        const headers = new Headers()
        headers.append("Authorization", 'Bearer ' + userToken)
        headers.append("Content-Type", "application/json")
        const body = JSON.stringify({
          name: configurationName,
          options: Object.entries(selection).map(elt => elt[1])
        })
        fetch('http://localhost:8080/user/configurations', {
          method: 'POST',
          headers, body
        })
        .then((res) => {
          if (res.status === 201) {
            navigate("/account")
          }
        })
        .catch(err => console.error(err));
      }
    }}>
      Sauvegarder la configuration
    </Button>
    {
        configurationId && <Button variant="outlined" onClick={() => {
          if (!checkConnection()) {
            return;
          }
          const isSelectionGood = checkSelectionBeforeAddToStore()
          if (isSelectionGood) {
            const configurationName = prompt(
                "Choisissez un nom pour votre configuration");
            const headers = new Headers()
            headers.append("Authorization", 'Bearer ' + userToken)
            headers.append("Content-Type", "application/json")
            const body = JSON.stringify({
              name: configurationName,
              options: Object.entries(selection).map(elt => elt[1])
            })
            fetch('http://localhost:8080/user/configurations/' + configurationId, {
              method: 'PUT',
              headers, body
            })
            .then((res) => {
              if (res.status === 200) {
                navigate("/account")
              }
            })
            .catch(err => console.error(err));
          }
        }}>
          Mettre à jour la configuration
        </Button>
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
    <Button variant="outlined" onClick={() => {
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
        .then(res => res.json())
        .then(() => navigate("/basket"))
        .catch(err => console.error(err));
      }
    }}>
      Ajouter au panier
    </Button>
    {
        commandId && <Button variant="outlined" onClick={() => {
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
            .then(res => res.json())
            .then(() => navigate("/basket"))
            .catch(err => console.error(err));
          }
        }}>
          Mettre à jour la commande
        </Button>
    }
  </div>
}
