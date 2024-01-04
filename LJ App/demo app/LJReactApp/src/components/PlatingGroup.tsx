import { useState } from "react";

function PlatingGroup() { //create list of items to select
    let platingName = ["Cobalt", "Gold", "Platinum", "Silver", "Stainless Steel", "Titanium", "Tungsten", "Brass", "Copper", "Chronium", "Sterling Silver", "Rhodium"];
    const [selectedIndex, setSelectedIndex] = useState(-1);
    //Hook (useState), this component can have data or state that can change over time
    
    
  



  return (
    <>
        <h1>Plating</h1>
        {platingName.length === 0 && <p>No item found</p> }
        <ul className="list-group">
         {platingName.map((item, index) => (
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

export default PlatingGroup;
