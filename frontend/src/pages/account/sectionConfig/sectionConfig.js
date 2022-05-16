import './sectionConfig.scss';
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";

export default function SectionConfig({options, name, deleteConfig, id, updateConfigURL}) {
    const isAColor = (value, type) => {
        return (
            (type === "COLOR")
                ? <div className="colordiv" style={{backgroundColor: value}}/>

                : value
        );
    };
    function deleteConfiguration(){
        deleteConfig(id);
    };
    return (
      <div className="config">
          <div>
                <div className="configTitle">
                    <h2>{name}</h2>
                </div>
                <div className="imgCommand">
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
        <div className="buttons">
          <Link to={updateConfigURL(options, id)}><button className="btn styledButton">Modifier</button></Link>
          <button className="btn styledButton" onClick={deleteConfiguration}>Supprimer</button>
        </div>

      </div>
    );
}
