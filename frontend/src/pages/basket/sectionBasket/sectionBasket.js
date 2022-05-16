import './sectionBasket.scss';
import {useNavigate} from "react-router-dom";

export default function SectionBasket({options, quantity, commandId, isBuy, buy, link}) {
    const navigate = useNavigate();
    const isAColor = (value, type) => {
        return (
            (type === "COLOR")
                ? <div className="colordiv" style={{backgroundColor: value}}/>

                : value
        );
    };

    const update = () => {
        navigate(link);
    }

    const clickBuy = () => {
        buy(commandId);
    };


    return (
      <div className="command">
        <div className="imgCommand">
            <img src="/clef/twoClef.png" alt="usb clef"/>
        </div>
        <div className="options">
            {
                options.map(({
                                 attributeId,
                                 attributeName,
                                 optionValue,
                                 optionType
                             }) => {
                    return (
                        <div className={"value"} key={attributeId}>
                            <div className="text">
                                {attributeName} : {isAColor(optionValue, optionType)}
                            </div>
                        </div>
                    );
                })
            }
        </div>
        <div className="quantity">
        </div>
        <div className="buttonContainer">
            <p>Quantité: {quantity}</p>
            {
              !isBuy ?
                  <div className="buttons">
                    <button onClick={clickBuy} className="btn styledButton">Acheter</button>
                    <button onClick={update} className="btn styledButton">Modifier</button>
                  </div>
                  : null
            }
       </div>
      </div>
    );
}