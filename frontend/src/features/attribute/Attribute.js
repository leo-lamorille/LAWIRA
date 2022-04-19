import './Attribute.scss';
import AttributeOption from "../attributeOption/AttributeOption";

export default function ({id, name, description, options, selectedOptionId, clickOnOption}) {
    function _clickOnOption(optionId) {
        clickOnOption(id, optionId);
    }
    return <div className="attribute">
        <h2>{name}</h2>
        <p>{description}</p>
        <div>
            {
                options.map(({id, type, value}) => <AttributeOption key={id} id={id} type={type}
                                                                    value={value} clickOnOption={_clickOnOption}
                                                                    selected={selectedOptionId === id}/>)
            }
        </div>
    </div>
}