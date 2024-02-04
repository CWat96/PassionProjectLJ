import { useState } from "react";

function StoneGemGroup() { //create list of items to select
    let gemsStone = [
      "Diamond",
      "Ruby",
      "Pearl",
      "Lab Grown Diamond",
      "Aquamarine",
      "Opal",
      "Moissanite Diamond",
      "Sapphire",
      "Topaz",
      "Emerald",
      "Quartz",
      "Peridot",
      "Cubic Zirconia",
      "Testing",
    ];
    const [selectedIndex, setSelectedIndex] = useState(-1);
    //Hook (useState), this component can have data or state that can change over time
    
    
  



  return (
    <>
        <h1>Stone</h1>
        {gemsStone.length === 0 && <p>No item found</p> }
        <ul className="list-group">
         {gemsStone.map((item, index) => (
         <li 
           className={
           selectedIndex === index
           ? "list-group-item active"
           : "list-group-item"
            }
            key={item}
            onClick={() => { setSelectedIndex(index); }}
            >
             {item}
             </li> // reoccuring clicks on specific name
         ))}
        </ul>
    </>
  );
}

export default StoneGemGroup;
