import './sectionBasket.scss';
import {useNavigate} from "react-router-dom";

export default function SectionBasket({options, quantity, buy, isBuy, link, commandId}) {
    const navigate = useNavigate();

    const isAColor = (value, name) => {
        return (
            (name === 'Couleur' || name === 'Leds')
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
                             }) => {
                    return (
                        <div className={"value"} key={attributeId}>
                            <div className="text">
                                <p className="attributeName">{attributeName} :</p> {isAColor(optionValue, attributeName)}
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
                  <button onClick={clickBuy} className="btn styledButton">Acheter</button>
                  : null
            }
            <button onClick={update} className="btn styledButton">Modifier</button>
       </div>
      </div>
    );
}