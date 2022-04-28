import './Attribute.scss';
import AttributeOption from "../attributeOption/AttributeOption";

export default function Attribute({
  id,
  name,
  description,
  options,
  selectedOptionId,
  clickOnOption
}) {
  function _clickOnOption(optionId) {
    clickOnOption(id, optionId);
  }

  function isOptionSelected(optionId) {
    let res = selectedOptionId === optionId
    return res;
  }

  return <div className="attribute">
    <h2>{name}</h2>
    <div>{description}</div>
    <div>
      {options.map(({id: optionId, type, value}) => {
        return <AttributeOption id={optionId}
                                attributeName={name}
                                value={value}
                                type={type}
                                clickOnOption={_clickOnOption}
                                selected={isOptionSelected(optionId)}/>
      })}
    </div>
  </div>
}
