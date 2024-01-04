function Message() {
    const name = 'Cesily';
    if (name)
    return <h1>Hello {name}</h1>;
    return <h1>Hello Guest</h1>;
}

export default Message;