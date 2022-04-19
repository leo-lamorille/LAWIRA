import './product.scss';
import {useEffect, useState} from "react";
import {Button, CircularProgress, TextField} from "@mui/material";
import Attribute from "../../features/attribute/Attribute";
import {useLocation, useNavigate} from "react-router-dom";
import {parseSearchRequest, selectionToLocation} from "../../util";

export default function Product() {
    const [attributes, setAttributes] = useState([]);
    const [quantity, setQuantity] = useState(1);
    const [errorMessage, setErrorMessage] = useState("")
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8080/public/attributes')
            .then((res) => res.json())
            .then(res => setAttributes(res));
    }, [])

    if (attributes.length === 0) return <div className="product__page">
        <div className="progress">
            <CircularProgress style={{
                margin: "auto"
            }}/>
        </div>
    </div>;


    const selection = {}
    const requestParams = parseSearchRequest(location);
    attributes.forEach(({id}) => {
        const optionId = requestParams[id];
        if (optionId !== undefined) {
            selection[id] = optionId
        }
    })

    function clickOnOption(attributeId, optionId) {
        const newSelection = Object.assign(selection);
        newSelection[attributeId] = optionId;
        const url = location.pathname + selectionToLocation(newSelection);
        navigate(url);
    }

    function checkSelectionBeforeAddToStore() {
        const nbAttributes = attributes.length;
        const nbSelection = Object.entries(selection).length;
        if (nbSelection !== nbAttributes) {
            setErrorMessage("Erreur: vous avez oublié des configurations ...");
            return false;
        }
        if (quantity < 1){
            setErrorMessage("Erreur: La quantité doit être supérieure à 1");
            return true;
        }
        setErrorMessage("")
        return nbSelection === nbAttributes && quantity >= 1;
    }

    return <div className="product__page">
        <h1>PRODUIT</h1>
        {
            attributes.map(({id, name, description, options}) => <Attribute key={id} id={id} name={name}
                                                                            description={description}
                                                                            options={options}
                                                                            selectedOptionId={selection[id]}
                                                                            clickOnOption={clickOnOption}/>)
        }

        <TextField className="quantity" value={quantity} type="text" label="Quantité"
                   focused onChange={(event) => {
            let newQuantity = parseInt(event.target.value)
            newQuantity = isNaN(newQuantity) ? 0 : newQuantity;
            setQuantity(newQuantity);
        }}/>
        {
            <div className="errorMessage">{errorMessage}</div>
        }
        <Button variant="outlined" onClick={() => {
            const isSelectionGood = checkSelectionBeforeAddToStore()
            if (isSelectionGood) {
                // fetch
                console.log("Ajouter panier (fetch to do)")
            }
        }}>
            Ajouter au panier
        </Button>
    </div>
}

/**
 * <div className="quantity">
 *             <span>Quantity: </span>
 *             <input type={"text"} value={quantity} onChange={(event) => {
 *                 let newQuantity = parseInt(event.target.value)
 *                 newQuantity = isNaN(newQuantity) ? 0 : newQuantity;
 *                 setQuantity(newQuantity);
 *             }}/>
 *         </div>
 */