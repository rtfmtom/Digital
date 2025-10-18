# About

This is a slightly modified fork of Digital. The only change is a customized TCP interface that allows read/write access to the RAMDualPort chip of a running simulation.

I may generalize this feature in the future, but for now it's a "hack" to enable demo applications that interact with the simulated Hack CPU in real-time.

For full documentation on the Digital application, see the [original repository](https://github.com/hneemann/Digital).

# Usage
```sh
git clone https://github.com/rtfmtom/Digital.git && cd Digital
mvn clean install
/usr/bin/java -jar target/Digital.jar
```

On startup, Digital creates a TCP server listening on port 41114 by default.

To enable remote interface access for a circuit:

1. Open the circuit you want to enable
2. Navigate to Edit → Settings → Advanced
3. Check 'Allow remote connection'

<img width="448" height="444" alt="Enable Remote" src="https://github.com/user-attachments/assets/e3f2fe1e-ffde-4981-810a-5f3209473e28" />

### Usage with Hack CPU

For remotely interacting with the [Hack CPU](https://github.com/rtfmtom/CPU) specifically, see [this repository]().
