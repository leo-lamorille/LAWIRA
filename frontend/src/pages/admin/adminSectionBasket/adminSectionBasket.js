import './adminSectionBasket.scss';
import {useEffect, useState} from "react";

export default function AdminSectionBasket({options, quantity, username, id, validate}) {
    const isAColor = (value, type) => {
        return (
            (type === "COLOR")
                ? <div className="colordiv" style={{backgroundColor: value}}/>

                : value
        );
    };

    return (
      <div className="adminBasket">
        <div className="imgCommand">
            <div>
                {username}
            </div>
            <div>
                <img src="/clef/twoClef.png" alt="usb clef"/>
            </div>
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
            <p>Quantité: {quantity}</p>
        </div>
          <button className="btn styledButton" onClick={()=>validate(id)}>Valider</button>
      </div>
    );
}