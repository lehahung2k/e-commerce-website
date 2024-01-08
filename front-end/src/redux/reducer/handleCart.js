const cart = []

const handleCart = (state=cart, action) =>{
    const product = action.payload
    switch(action.type){
        case "SET_CART":
            return action.payload; // Set the entire cart from server
        case "ADDITEM":
            // Check if product already in cart
            const exist = state.find((x) => x.productId === product.productId)
            if(exist){
                // Increase the quantity
                return state.map((x)=>
                    x.productId === product.productId ? {...x, count: x.count+1} : x
                )
            }
            else{
                return [...state, {...product, count:1}]
            }
        case "DELITEM":
            const exist2 = state.find((x) => x.productId === product.productId)
            if(exist2.count === 1){
                return state.filter((x)=>x.productId!==exist2.productId)
            }
            else{
                return state.map((x)=> x.productId===product.productId?{...x, count:x.count-1}:x)
            }

        default:
            return state
    }
}

export default handleCart