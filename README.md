# Trivium
Implementation of the Trivium streaming cryptography schema.

Trivium is a synchronous stream cipher designed to provide a flexible trade-off between speed and gate count in hardware, and reasonably efficient software implementation. In this implementation, a user would be asked to input the plaintext, then be able to view the ciphertext and the decrypted plaintext.

This project implements a light weight Trivium stream cipher.The Encryption asks for a plaintext, then goes through all letters and encrypt them with Trivium to generate a ciphertext. The Decryption asks for a ciphertext, then goes through all letters and decrypt them to recover the plaintext.

What it does:

1) Initialize the IV, Key and C:

Key = 80000000000000000000 and IV = 00000000000000000000

2) Key Stream Generation

3) Encryption using key stream and XOR with user input, then display.

4) Reverse the process and decrypt the ciphertext, then display.

