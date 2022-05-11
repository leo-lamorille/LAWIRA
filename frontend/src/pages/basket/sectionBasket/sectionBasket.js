import './sectionBasket.scss';
import {useEffect, useState} from "react";

export default function SectionBasket({options, quantity}) {
    const isAColor = (value, name) => {
        return (
            (name === 'Couleur' || name === 'Leds')
                ? <div className="colordiv" style={{backgroundColor: value}}/>

                : value
        );
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
                                {attributeName} : {isAColor(optionValue, attributeName)}
                            </div>
                        </div>
                    );
                })
            }
        </div>
        <div className="quantity">
            <p>Quantité: {quantity}</p>
        </div>
      </div>
    );
}